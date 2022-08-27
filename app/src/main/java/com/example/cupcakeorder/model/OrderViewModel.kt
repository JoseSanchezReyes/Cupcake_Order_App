package com.example.cupcakeorder.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _nameUser = MutableLiveData<String>()
    val nameUser: LiveData<String> = _nameUser

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    //Dar formato de moneda al precio con Transformations.map()
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions = getPickupOptions() //Fecha de opciones

    init {
        resetOrder()
    }

    /* --------------------------SET VALUES IN FRAGMENTS -------------------------------- */
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setName(nameUsr: String) {
        _nameUser.value = nameUsr
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    /* ---------------------------------START FRAGMENT ----------------------------------- */
    //Verify if flavor is set or Empty in Start Fragment
    fun hasNoFlavorSet(): Boolean = _flavor.value.isNullOrEmpty()

    /* ---------------------------------PICKUP FRAGMENT ---------------------------------- */
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d y", Locale.getDefault())
        val calendar = Calendar.getInstance() //Contiene fecha y hora actual
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1) //Aumenta el calendario en un dia
        }
        return options
    }

    /* -------------------------------GENERAL FUNCTIONS ---------------------------------- */
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
        _nameUser.value = ""
    }

    private fun updatePrice() {
        var calculatedPrice = (_quantity.value ?: 0) * (PRICE_PER_CUPCAKE)
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

}