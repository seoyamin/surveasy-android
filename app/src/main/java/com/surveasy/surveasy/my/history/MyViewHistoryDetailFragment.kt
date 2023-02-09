package com.surveasy.surveasy.my.history

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.surveasy.surveasy.R
import com.surveasy.surveasy.databinding.FragmentMyViewHisotryDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class MyViewHistoryDetailFragment : Fragment() {
    val storage = Firebase.storage
    val model by activityViewModels<MyViewHistoryDetailViewModel>()
    private var _binding : FragmentMyViewHisotryDetailBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyViewHisotryDetailBinding.inflate(layoutInflater)
        val view = binding.root
        val lastImg = view.findViewById<ImageView>(R.id.historyDetailLastCapture)
        val alert = view.findViewById<TextView>(R.id.historyDetailAlert)
        val alert2 = view.findViewById<TextView>(R.id.historyDetailAlert2)
        val uploadBtn = view.findViewById<Button>(R.id.historyDetailUploadBtn)
        val noneBtn = view.findViewById<Button>(R.id.historyDetailNoneBtn)

        CoroutineScope(Dispatchers.Main).launch {
            fetchModel()

            if(model.detailModel[0].title.length>15){
                binding.historyDetailReward.text =
                    model.detailModel[0].title.substring(0,15)+"..."
            }else{
                binding.historyDetailReward.text = model.detailModel[0].title
            }
            binding.historyDetailReward.text = model.detailModel[0].reward.toString()+"원"
            binding.historyDetailDate.text = "참여일자 : ${model.detailModel[0].date}"

            if(model.progress[0].progress<3){
                fetchLastImg(model.detailModel[0].id, model.filePath[0].filePath)
            }else{
                binding.historyDetailLastCapture.visibility = View.GONE
                binding.historyDetailAlert.visibility = View.GONE
                binding.historyDetailAlert2.visibility = View.VISIBLE
                binding.historyDetailUploadBtn.visibility = View.GONE
                binding.historyDetailNoneBtn.visibility = View.VISIBLE
            }

            binding.historyDetailUploadBtn.setOnClickListener {
                if(model.progress[0].progress<3){
                    val intent = Intent(context, MyViewUpdatePhotoActivity::class.java)
                    intent.putExtra("filePath", model.filePath[0].filePath)
                    //storage 폴더 접근 위해
                    intent.putExtra("id", model.detailModel[0].id)
                    intent.putExtra("idChecked", model.detailModel[0].lastId)
                    startActivity(intent)
                    (activity as MyViewHistoryDetailActivity).activityFinish()
                    //(activity as MyViewHistoryActivity).finishActivity()
                }else{
                    Toast.makeText(context,"마감된 설문은 완료 화면 변경이 불가합니다.", Toast.LENGTH_LONG).show()
                }
            }

        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun fetchModel(){
        withContext(Dispatchers.IO){
            while (model.detailModel.size==0 || model.progress.size==0 || model.filePath.size==0){ }
        }
        //Log.d(TAG, "모델 개수 : ${model.filePath}")
    }

    // 기존에 첨부한 이미지 보여주기
    private fun fetchLastImg(id : Int, filePath : String?) {
        val storageRef: StorageReference = storage.reference.child(id.toString())
        val file1: StorageReference = storageRef.child(filePath.toString())

        Glide.with(this).load(R.raw.app_loading).into(binding.historyDetailLastCapture)

        file1.downloadUrl.addOnSuccessListener { item ->
            Glide.with(this).load(item).into(binding.historyDetailLastCapture)
        }.addOnFailureListener{
            binding.historyDetailLastCapture.visibility = View.GONE
            binding.historyDetailAlert.visibility = View.GONE
            binding.historyDetailAlert2.visibility = View.VISIBLE
            binding.historyDetailUploadBtn.visibility = View.GONE
            binding.historyDetailNoneBtn.visibility = View.VISIBLE
        }
    }

}