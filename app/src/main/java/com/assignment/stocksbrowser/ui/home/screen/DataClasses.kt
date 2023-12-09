package com.assignment.stocksbrowser.ui.home.screen

data class stock_detail(
    val name:String,
    val current_price:Double,
    val limit_up_price:Double,
    val limit_down_price:Double,
    val pb:Double,
    val roe:Double,
    val roa:Double,
    val bid_volumn:Int,
    val bid_price:Double,
    val ask_volumn:Int,
    val ask_price:Double
)
var stock_list= listOf(
    stock_detail("AAPL",35.toDouble(),34.toDouble(),40.toDouble(),3.2,4.4,4.5,200,35.1,100,34.8),
    stock_detail("GOOG",49.toDouble(),34.toDouble(),54.toDouble(),10.2,6.4,3.5,500,55.1,200,44.8)
)


data class profile(
    val user:String,
    val email:String,
    val DOB:String,
    val totalAssets : Double,
    val purchasingPower:Double

)
var userProfile= listOf(profile("Brian Duong","BrianDuong3003@gmail.com","30/03/2003",10000.toDouble(),25000.toDouble()))


data class Stock(
    val name: String,
    val price: Double,
    val change: Double,
    val volume: Int
)

data class notify(
    val status: String,
    val name: String,
    val price: Double=0.0,
    val volume: Int=0
)
data class blank(val str:String="")
val blanked= listOf(blank(""),blank(""))
var stocks = mutableListOf(
    Stock("ACB", 22.7, -0.20, 100),
    Stock("BCM", 61.4, 0.0, 200),
    Stock("BID", 42.9, 0.25, 300),
    Stock("BVH", 40.8, -0.05, 100),
    Stock("CTG", 29.7, -0.15, 500),
    Stock("FPT", 91.0, -0.3, 100),
    Stock("FPT", 91.0, 0.0, 100)
)
val stockHistoryList= mutableListOf(
    notify("Buy  ","AAPL",35.toDouble(),100),
    notify("Sell ","AAPL",40.toDouble(),100),
    notify("Alert","AAPL"),
    notify("Sell ","AAPL",40.toDouble(),100),
    notify("Buy  ","AAPL",35.toDouble(),100),
    notify("Sell ","AAPL",40.toDouble(),100),
    notify("Alert","AAPL"),
    notify("Sell ","AAPL",40.toDouble(),100)
)


data class Alert(
    var buyStatus: String?,
    var sellStatus: String?,
    val name: String,
    var buyPriceAlert: Double = 0.0,
    var sellPriceAlert: Double = 0.0
)
var alertListings: MutableList<Alert> = mutableListOf()

fun alert(autocustom: String, status: String, name: String, price: Double = 0.0) {
    val stock = stocks.find { it.name == name }

    if (stock != null && status == "Alert") {
        // Save to stockHistoryList for "Alert" status
        val Notify = notify(status, name)
        stockHistoryList.add(Notify)

        // Save to alertListings
        var alert = alertListings.find { it.name == name }

        if (alert == null) {
            alert = Alert(null, null, name, 0.0, 0.0)
            alertListings.add(alert)
        }

        if (autocustom == "auto") {
            if (status == "Buy") {
                alert.buyStatus = "Buy"
                alert.buyPriceAlert = stock.price * (1 - 0.1)
            } else if (status == "Sell") {
                alert.sellStatus = "Sell"
                alert.sellPriceAlert = stock.price * (1 + 0.1)
            }

        } else if (autocustom == "custom") {
            if (status == "Buy") {
                alert.buyStatus = "Buy"
                alert.buyPriceAlert = price
            } else if (status == "Sell") {
                alert.sellStatus = "Sell"
                alert.sellPriceAlert = price
            }
        }

        println("Alert generated: ${status} ${name} at ${if (status == "Buy") alert.buyPriceAlert else alert.sellPriceAlert}")
    } else if (stock == null) {
        println("Stock not found for $name")
    } // Ignore other statuses for stockHistoryList
}
