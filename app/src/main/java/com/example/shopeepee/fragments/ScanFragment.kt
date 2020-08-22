package com.example.shopeepee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.shopeepee.R
import com.example.shopeepee.controllers.ScanController
import com.example.shopeepee.viewmodels.ShoppingListsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ScanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScanFragment : Fragment() {
    var viewModel = ViewModelProvider(this.activity!!).get(
        ShoppingListsViewModel::class.java
    )
    private val args: ScanFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.shoppingId?.let { ScanController.data(it) }
        ScanController.init(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ScanController.restoreState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    companion object {
        fun newInstance(): ScanFragment {
            return ScanFragment()
        }
    }
}
