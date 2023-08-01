package com.rianjaradev.androidtemplate.presentation.ui.feature1

import com.rianjaradev.androidtemplate.domain.entity.Feature1Entity

sealed class Feature1Action {
    object Fetch : Feature1Action()
    object CancelFetch : Feature1Action()
}

sealed class Feature1UIState {
    class ShowResponse(val feature1Entity: Feature1Entity) : Feature1UIState()
    object CleanResponse : Feature1UIState()
}