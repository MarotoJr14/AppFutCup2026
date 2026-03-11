package com.futcup.app.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.futcup.app.MainActivity
import com.futcup.app.R
import com.futcup.app.model.Partido

class CalendarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_calendar, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = (requireActivity() as MainActivity).torneoData ?: return
        val container = view.findViewById<LinearLayout>(R.id.ll_partidos)

        val porRonda = data.partidos.groupBy { it.ronda }
            .entries.sortedBy { it.value.first().orden_ronda }

        porRonda.forEach { (ronda, partidos) ->
            container.addView(crearCabeceraRonda(ronda))
            partidos.forEach { partido ->
                container.addView(crearCardPartido(partido))
            }
        }
    }

    private fun crearCabeceraRonda(ronda: String): TextView {
        return TextView(requireContext()).apply {
            text = ronda.uppercase()
            textSize = 13f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.gold_primary))
            val dp16 = (16 * resources.displayMetrics.density).toInt()
            val dp8 = (8 * resources.displayMetrics.density).toInt()
            setPadding(dp16, dp16, dp16, dp8)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = dp8 }
        }
    }

    private fun crearCardPartido(partido: Partido): View {
        val inflater = LayoutInflater.from(requireContext())
        val card = inflater.inflate(R.layout.item_partido, null)

        card.findViewById<TextView>(R.id.tv_hora).text = partido.hora
        card.findViewById<TextView>(R.id.tv_local).text = partido.equipo_local
        card.findViewById<TextView>(R.id.tv_visitante).text = partido.equipo_visitante
        card.findViewById<TextView>(R.id.tv_resultado).text = partido.getResultado()

        val dp8 = (8 * resources.displayMetrics.density).toInt()
        val dp16 = (16 * resources.displayMetrics.density).toInt()
        card.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(dp16, 0, dp16, dp8) }

        return card
    }
}
