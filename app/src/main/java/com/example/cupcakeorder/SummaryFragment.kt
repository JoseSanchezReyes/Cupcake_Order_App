package com.example.cupcakeorder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcakeorder.databinding.FragmentSummaryBinding
import com.example.cupcakeorder.model.OrderViewModel
import com.google.android.material.snackbar.Snackbar

class SummaryFragment : Fragment() {
    private var binding: FragmentSummaryBinding? = null

    //viewModel compartido
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentSummaryBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            summaryFragment = this@SummaryFragment
        }
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_summaryFragment_to_startFragment)
    }

    fun sendOrder() {
        val numberOfCupcakes = sharedViewModel.quantity.value ?: 0
        val nameOfUser = sharedViewModel.nameUser.value.toString()
        val orderSummary = getString(
            R.string.order_details,
            resources.getQuantityString(R.plurals.cupcakes, numberOfCupcakes, numberOfCupcakes),
            sharedViewModel.flavor.value.toString(),
            sharedViewModel.date.value.toString(),
            sharedViewModel.price.value.toString()
        )

        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_EMAIL, arrayOf("correo@correo.com"))
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_cupcake_order, nameOfUser))
            .putExtra(Intent.EXTRA_TEXT, orderSummary)
        /* comprobar si hay una app que pueda abrir mi intent */
        @Suppress("DEPRECATION")
        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        }

        Toast.makeText(activity, "Send Order to ${sharedViewModel.nameUser.value}", Toast.LENGTH_SHORT).show()
        binding?.let {
            Snackbar.make (it.root, "Send Order to ${sharedViewModel.nameUser.value}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}