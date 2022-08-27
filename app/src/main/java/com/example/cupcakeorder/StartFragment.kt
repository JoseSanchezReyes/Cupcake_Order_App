package com.example.cupcakeorder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcakeorder.databinding.FragmentStartBinding
import com.example.cupcakeorder.model.OrderViewModel


class StartFragment : Fragment() {
    private var binding: FragmentStartBinding? = null

    //viewModel compartido
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // vinculación de objetos de escucha para asociar los objetos de escucha de clics del botón
        binding?.startFragment = this

    }

    fun orderCupcake(quantity: Int) {
        val nameUsr = binding?.textInputEditTextName?.text.toString()
        if (nameUsr.isEmpty() ) {
            setErrorTextField(true)
            Toast.makeText(activity, "Please enter a correct name", Toast.LENGTH_SHORT).show()
            return
        }
        setErrorTextField(false)

        sharedViewModel.setQuantity(quantity)
        sharedViewModel.setName(nameUsr)

        if (sharedViewModel.hasNoFlavorSet()) {
            sharedViewModel.setFlavor(getString(R.string.vanilla))
        }
        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
        Toast.makeText(activity, "Ordered $quantity cupcake(s)", Toast.LENGTH_SHORT).show()
    }

    //Establece un error y resetea el texto
    private fun setErrorTextField(error: Boolean) {
        binding?.apply {
            if (error){
                textFieldName.isErrorEnabled = true
                textFieldName.error = getString(R.string.try_again)
            } else {
                textFieldName.isErrorEnabled = false
                textFieldName.error = null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}