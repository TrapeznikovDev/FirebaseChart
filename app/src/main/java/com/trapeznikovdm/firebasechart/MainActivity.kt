package com.trapeznikovdm.firebasechart

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.trapeznikovdm.firebasechart.databinding.ActivityMainBinding
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setupActionbar()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")


        binding.buttonSent.setOnClickListener{
            myRef.child(myRef.push().key?:"empty").setValue(User(auth.currentUser?.displayName, binding.edMessage.text.toString()))
        }
        onChangeListener(myRef)
        initRcView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sign_out){
            auth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onChangeListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<User>()
                for (s in snapshot.children){
                    val user = s.getValue(User::class.java)
                    if (user != null){
                        list.add(user)
                    }
                }
                adapter.submitList(list)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setupActionbar(){
        val ab = supportActionBar
        GlobalScope.launch(Dispatchers.IO) {
            val bMap = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val drawableIcon = BitmapDrawable(resources, bMap)

            withContext(Dispatchers.Main){
                ab?.setDisplayHomeAsUpEnabled(true)
                ab?.setHomeAsUpIndicator(drawableIcon)
                ab?.title = auth.currentUser?.displayName
            }

        }

    }

    private fun initRcView() = with(binding){
        adapter = RecyclerAdapter()
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.adapter = adapter
    }
}