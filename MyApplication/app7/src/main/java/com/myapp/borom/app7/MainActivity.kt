package com.myapp.borom.app7

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


interface OnChangeListener {
    fun onDataChanged()
}


class MainActivity : AppCompatActivity(), OnChangeListener {

    var products = ArrayList<Product>()
    var productListAdapter: ProductListAdapter?=null

    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // создаем адаптер
        fillData()
        productListAdapter = ProductListAdapter(this, products,this)

        // настраиваем список
        val lvMain = findViewById<View>(R.id.lvMain) as ListView
        lvMain.setAdapter(productListAdapter)
    }

    // генерируем данные для адаптера
    fun fillData() {
        for (i in 1..20) {
            products.add(Product("Product $i", i * 1000,
                    R.drawable.ic_product, false))
        }
    }

    override fun onDataChanged() {
        var count = productListAdapter?.checkedCount
        if (count != null && count > 0) {
            btn_show_checked.setText("Show checked ($count)")
            btn_show_checked.isEnabled=true
        } else{
            btn_show_checked.setText(getString(R.string.box))
            btn_show_checked.isEnabled=false
        }

    }
    // выводим информацию о корзине
    fun showResult(v: View) {
        var result = "Товары в корзине:"
        for (p in productListAdapter!!.list) {
            if (p.box)
                result += "\n" + p.name
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show()
    }

}
