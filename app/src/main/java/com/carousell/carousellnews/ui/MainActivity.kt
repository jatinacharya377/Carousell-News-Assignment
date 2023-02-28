package com.carousell.carousellnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.carousell.carousellnews.databinding.ActivityMainBinding

/**
 * This screen is one of the core component which represents our Android app.
 * It also contains all our fragments. FragmentHome, FragmentSplash, FragmentStates, FragmentViewAllTransactions.
 * @author: Jagannath Acharya
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }
}