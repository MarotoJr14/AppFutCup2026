package com.futcup.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.futcup.app.MainActivity
import com.futcup.app.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = (requireActivity() as MainActivity).torneoData ?: return

        view.findViewById<TextView>(R.id.tv_torneo_nombre).text = data.torneo.nombre
        view.findViewById<TextView>(R.id.tv_campo).text = "📍 ${data.torneo.campo}"
        view.findViewById<TextView>(R.id.tv_fecha).text =
            if (data.torneo.fecha_inicio == data.torneo.fecha_fin)
                "📅 ${data.torneo.fecha_inicio}"
            else
                "📅 ${data.torneo.fecha_inicio} — ${data.torneo.fecha_fin}"

        val container = view.findViewById<LinearLayout>(R.id.ll_equipos)
        data.equipos.forEach { equipo ->
            val tv = TextView(requireContext()).apply {
                textSize = 16f
                setPadding(0, 16, 0, 16)
                setTextColor(resources.getColor(R.color.text_primary, null))
            }
            val divider = View(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1
                )
                setBackgroundColor(resources.getColor(R.color.divider, null))
            }
            container.addView(tv)
            container.addView(divider)
        }
    }
}
