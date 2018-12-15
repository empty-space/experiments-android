package com.myapp.borom.app7

import java.util.ArrayList;
import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.*

class ProductListAdapter ( var ctx: Context,  var objects: ArrayList<Product>,val onChangeListener:OnChangeListener) : BaseAdapter() {

    var checkedCount:Int=0
        private set
    var lInflater: LayoutInflater

    init {
        lInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    // содержимое корзины
    // если в корзине
    val list: ArrayList<Product>
        get() {
            val box = ArrayList<Product>()
            for (p in objects) {
                if (p.box)
                    box.add(p)
            }
            return box
        }

    // обработчик для чекбоксов
     private var myCheckChangeList: CompoundButton.OnCheckedChangeListener = object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton,
                                      isChecked: Boolean) {
            // меняем данные товара (в корзине или нет)
            getProduct(buttonView.tag as Int).box = isChecked
            updateCounter(isChecked)
            onChangeListener.onDataChanged()
        }
    }

    fun updateCounter(isChecked: Boolean){
        if(isChecked) checkedCount++
        else if(checkedCount>0)checkedCount--;


    }

    // кол-во элементов
    override fun getCount(): Int {
        return objects.size
    }

    // элемент по позиции
    override fun getItem(position: Int): Any {
        return objects[position]
    }

    // id по позиции
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // пункт списка
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // используем созданные, но не используемые view
        var view: View? = convertView
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false)
        }

        val p = getProduct(position)

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        (view!!.findViewById(R.id.tvDescr) as TextView).text = p.name
        (view!!.findViewById(R.id.tvPrice) as TextView).text = "$"+p.price.toString()
        (view!!.findViewById(R.id.ivImage) as ImageView).setImageResource(p.image)

        val cbBuy = view!!.findViewById(R.id.cbBox) as CheckBox
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangeList)
        // пишем позицию
        cbBuy.tag = position
        // заполняем данными из товаров: в корзине или нет
        cbBuy.isChecked = p.box
        return view
    }

    // товар по позиции
     fun getProduct(position: Int): Product {
        return getItem(position) as Product
    }
}
