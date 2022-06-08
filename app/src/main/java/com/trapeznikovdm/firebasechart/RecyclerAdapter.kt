package com.trapeznikovdm.firebasechart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trapeznikovdm.firebasechart.databinding.UserListItemBinding
import java.lang.Byte.decode
import java.lang.Integer.decode
import java.util.*

class RecyclerAdapter: ListAdapter<User, RecyclerAdapter.RecyclerViewHolder>(itemComparator()) {

    class RecyclerViewHolder(private val binding: UserListItemBinding, val context: Context): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("CheckResult")
        fun bind(user: User) = with(binding){
            messageView.text = user.message
            userName.text = user.name
            if(user.img!=null){
                imageView.visibility = View.VISIBLE
                Glide.with(context).asBitmap()
                    .load(Base64.decode(user.img, Base64.DEFAULT)).into(imageView)
            }

        }
        companion object{
            fun create(parent: ViewGroup): RecyclerViewHolder{
                return RecyclerViewHolder(UserListItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false), parent.context)
            }
        }
    }

    class itemComparator: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem ==newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem ==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.RecyclerViewHolder {
        return RecyclerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.RecyclerViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

}