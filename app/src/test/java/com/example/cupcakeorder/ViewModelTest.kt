package com.example.cupcakeorder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cupcakeorder.model.OrderViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class ViewModelTest {

    @get:Rule //Especificar que los objetos liveData no deben llamar al subproceso principal
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun quantity_twelve_cupcakes() {
        val viewModel = OrderViewModel()

        viewModel.quantity.observeForever {  }
        viewModel.setQuantity(12)

        assertEquals(12, viewModel.quantity.value)
    }

    @Test
    fun price_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.price.observeForever {  } //Si no observamos nos dara un error por el liveData
        viewModel.setQuantity(12)

        assertEquals("27,00 €", viewModel.price.value)
    }

    @Test
    fun date_cupcakes() {
        val viewModel = OrderViewModel()
        val firstDate = viewModel.dateOptions[0]
        viewModel.date.observeForever {  }
        viewModel.setDate(firstDate)
        assertEquals(/*firstDate */"lun jul 31 2023", viewModel.date.value)
    }
}