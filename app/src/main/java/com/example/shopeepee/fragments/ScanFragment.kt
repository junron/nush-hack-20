package com.example.shopeepee.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.*
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.shopeepee.R
import com.example.shopeepee.controllers.ScanController
import com.example.shopeepee.viewmodels.ShoppingListsViewModel
import com.google.firebase.ml.custom.*
import kotlinx.android.synthetic.main.fragment_scan.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.min

typealias ObjectListener = (luma: String) -> Unit

/**
 * A simple [Fragment] subclass.
 * Use the [ScanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScanFragment : Fragment() {
    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    val viewModel: ShoppingListsViewModel by activityViewModels()
    private val args: ScanFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.shoppingId?.let { ScanController.data(it, viewModel) }
        ScanController.init(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    activity,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity!!)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val objd = ObjectDetector { luma ->
                Log.d(TAG, "Object: $luma")
            }
            objd.load()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, objd)
                }

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.createSurfaceProvider())
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(activity!!))
    }

    private class ObjectDetector(private val listener: ObjectListener) : ImageAnalysis.Analyzer {
        val STRINGMAP: Array<String> = arrayOf(
            "apple",
            "brocolli",
            "can_beans",
            "carrot",
            "chipbag",
            "noodle",
            "potato",
            "soda"
        )
        lateinit var interpreter: FirebaseModelInterpreter
        lateinit var inputOutputOptions: FirebaseModelInputOutputOptions
        fun load() {
            val localModel = FirebaseCustomLocalModel.Builder()
                .setAssetFilePath("model.tflite")
                .build()

            val options = FirebaseModelInterpreterOptions.Builder(localModel).build()
            interpreter = FirebaseModelInterpreter.getInstance(options)!!

            inputOutputOptions = FirebaseModelInputOutputOptions.Builder()
                .setInputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 224, 224, 3))
                .setOutputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 8))
                .build()
        }

        fun Image.toBitmap(): Bitmap {
            val yBuffer = planes[0].buffer // Y
            val uBuffer = planes[1].buffer // U
            val vBuffer = planes[2].buffer // V

            val ySize = yBuffer.remaining()
            val uSize = uBuffer.remaining()
            val vSize = vBuffer.remaining()

            val nv21 = ByteArray(ySize + uSize + vSize)

            yBuffer.get(nv21, 0, ySize)
            vBuffer.get(nv21, ySize, vSize)
            uBuffer.get(nv21, ySize + vSize, uSize)

            val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
            val out = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
            val imageBytes = out.toByteArray()
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        fun Bitmap.toSquare(): Bitmap? {
            // get the small side of bitmap
            val side = min(this.width, this.height);

            // calculate the x and y offset
            val xOffset = (width - side) / 2
            val yOffset = (height - side) / 2

            // create a square bitmap
            // a square is closed, two dimensional shape with 4 equal sides
            return Bitmap.createBitmap(
                this, // source bitmap
                xOffset, // x coordinate of the first pixel in source
                yOffset, // y coordinate of the first pixel in source
                side, // width
                side // height
            )
        }

        @SuppressLint("UnsafeExperimentalUsageError")
        override fun analyze(image: ImageProxy) {
            var bitmap = image.image!!.toBitmap()
            bitmap = bitmap.toSquare()!!
            val batchNum = 0
            val input = Array(1) { Array(224) { Array(224) { FloatArray(3) } } }
            for (x in 0..223) {
                for (y in 0..223) {
                    val pixel = bitmap.getPixel(x, y)
                    input[batchNum][x][y][0] = Color.red(pixel) / 255.0f
                    input[batchNum][x][y][1] = Color.green(pixel) / 255.0f
                    input[batchNum][x][y][2] = Color.blue(pixel) / 255.0f
                }
            }

            val inputs = FirebaseModelInputs.Builder()
                .add(input) // add() as many input arrays as your model requires
                .build()

            interpreter.run(inputs, inputOutputOptions)
                .addOnSuccessListener { result ->
                    val output = result.getOutput<Array<FloatArray>>(0)[0]
                    var idxmax = -1;
                    var max = -1.0f;
                    (0..7).forEach { idx ->
                        if (output.get(idx) > max) {
                            max = output.get(idx)
                            idxmax = idx;
                        }
                    }
                    listener(STRINGMAP[idxmax])
                }.addOnFailureListener { e ->

                }


            image.close()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            activity!!.baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
