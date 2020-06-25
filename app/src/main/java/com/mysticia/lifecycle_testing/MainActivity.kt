package com.mysticia.lifecycle_testing

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SharedMemory
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import com.mysticia.lifecycle_testing.databinding.ActivityMainBinding
import com.mysticia.lifecycle_testing.model.Snacks
import timber.log.Timber

const val KEY_REVENUE = "key_revenue"
const val KEY_MEMES_SOLD = "key_meems_sold"
const val KEY_TIMER = "key_time"

class MainActivity : AppCompatActivity() , LifecycleObserver {

    private var revenue = 0
    private var snacksSold = 0
    private lateinit var SnackTimer : SnackTimer

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.i("Test onCreate")

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.memesButton.setOnClickListener {
            onMemesClicked()
        }

        SnackTimer = SnackTimer(lifecycle)

        if (savedInstanceState != null) {

            revenue = savedInstanceState.getInt(KEY_REVENUE)
            snacksSold = savedInstanceState.getInt(KEY_MEMES_SOLD)
            SnackTimer.secondsCount = savedInstanceState.getInt(KEY_TIMER)

        }

        binding.revenue = revenue
        binding.amountSold = snacksSold
        binding.memesButton.setImageResource(currentSnacks.imageId)

    }

    private fun onMemesClicked() {

        revenue += currentSnacks.price
        snacksSold ++

        binding.revenue = revenue
        binding.amountSold = snacksSold

        showCurrentSnacks()

    }

    private fun showCurrentSnacks() {

        var newSnacks = allmySnacks[0]
            for (snacks in allmySnacks) {

                if (snacksSold >= snacks.startCount) {
                    newSnacks = snacks
                }
                else break
            }

        if (newSnacks != currentSnacks) {
            currentSnacks = newSnacks
            binding.memesButton.setImageResource(newSnacks.imageId)
        }

    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText("Click Law ja")
            .setType("text/plain again")
            .intent

        try {
            startActivity(shareIntent)
        } catch (ex : ActivityNotFoundException) {
            Toast.makeText(this,"Share mai dai aow mai na",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart ma law ja")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_MEMES_SOLD, snacksSold)
        outState.putInt(KEY_TIMER, SnackTimer.secondsCount)
        super.onSaveInstanceState(outState)

        Timber.i("Save law ja")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.i("Yang yu")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("Stoped")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("Paused")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("WelComeback bro")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("OK BYE")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("Coming")
    }

    private val allmySnacks = listOf<Snacks>(
        Snacks(R.drawable.ic_launcher_background,10,0),
        Snacks(R.drawable.memes_1,15,5)
    )

    private var currentSnacks = allmySnacks[0]
}