package com.tainzhi.sample.api.recyclerview

import android.app.SearchManager
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tainzhi.sample.api.R
import com.tainzhi.sample.api.adapter.FilterableAdapter
import com.tainzhi.sample.api.widget.MyDividerItemDecoration
import com.tainzhi.sample.api.widget.RecyclerItemTouchHelper
import com.tainzhi.sample.api.widget.RecyclerItemTouchHelper.RecyclerItemTouchHelperListener

class RecyclerViewFilterableActivity : AppCompatActivity(), RecyclerItemTouchHelperListener {
    private var mCoordinatorLayoutRoot: CoordinatorLayout? = null
    private val mRecyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view)}
    private var mAdapter: FilterableAdapter? = null
    private var mSearchView: SearchView? = null
    private val mList: MutableList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_filterable)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        for (i in 0..29) {
            mList.add(i.toString())
        }
        mCoordinatorLayoutRoot = findViewById(R.id.cl_root)

        mRecyclerView.setLayoutManager(LinearLayoutManager(this, RecyclerView.VERTICAL, false))
        mRecyclerView.addItemDecoration(
            MyDividerItemDecoration(
                this, LinearLayout.VERTICAL,
                20
            )
        )
        mAdapter = FilterableAdapter(this, mList)
        mRecyclerView.setAdapter(mAdapter)

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        val simpleCallback: ItemTouchHelper.SimpleCallback = RecyclerItemTouchHelper(
            0,
            ItemTouchHelper.LEFT, this
        )
        ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        mSearchView = menu.findItem(R.id.action_search).actionView as SearchView
        mSearchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        mSearchView!!.maxWidth = Int.MAX_VALUE
        mSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mAdapter!!.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mAdapter!!.filter.filter(newText)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is FilterableAdapter.MyViewHolder) {
            val text = mList!![viewHolder.getAdapterPosition()]
            val deletedIndex = viewHolder.getAdapterPosition()
            val deletedItem = mList!![deletedIndex]
            mAdapter!!.removeItem(deletedIndex)
            val snackbar = Snackbar.make(
                mCoordinatorLayoutRoot!!, "$text removed from list!",
                Snackbar.LENGTH_LONG
            )
            snackbar.setAction("UNDO") { view: View? ->
                mAdapter!!.restoreItem(
                    deletedItem,
                    deletedIndex
                )
            }
            snackbar.setActionTextColor(Color.YELLOW)
            snackbar.show()
        }
    }

    override fun onBackPressed() {
        if (!mSearchView!!.isIconified) {
            mSearchView!!.isIconified = true
            return
        }
        super.onBackPressed()
    }
}