package com.example.cupcakeorder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcakeorder.databinding.FragmentPickupBinding
import com.example.cupcakeorder.model.OrderViewModel

class PickupFragment : Fragment() {
    private var binding: FragmentPickupBinding? = null

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
    ): View? {
        val fragmentBinding = FragmentPickupBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            pickupFragment = this@PickupFragment

            //nextButton.setOnClickListener { goToNextScreen() }
        }
    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_pickupFragment_to_summaryFragment)
        val toastSmsPickup = "Quantity: ${sharedViewModel.quantity.value} " +
                "NameUsr: ${sharedViewModel.nameUser.value} " +
                "Flavor: ${sharedViewModel.flavor.value} " +
                "Date: ${sharedViewModel.date.value}"
        Toast.makeText(activity, toastSmsPickup, Toast.LENGTH_SHORT).show()
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_pickupFragment_to_startFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}