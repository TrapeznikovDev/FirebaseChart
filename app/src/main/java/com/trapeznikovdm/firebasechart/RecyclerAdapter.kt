package com.trapeznikovdm.firebasechart

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trapeznikovdm.firebasechart.databinding.UserListItemBinding

class RecyclerAdapter: ListAdapter<User, RecyclerAdapter.RecyclerViewHolder>(itemComparator()) {

    class RecyclerViewHolder(private val binding: UserListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User) = with(binding){
            messageView.text = user.message
            userName.text = user.name
        }
        companion object{
            fun create(parent: ViewGroup): RecyclerViewHolder{
                return RecyclerViewHolder(UserListItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
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