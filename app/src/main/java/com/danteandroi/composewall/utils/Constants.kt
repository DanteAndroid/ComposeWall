/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.danteandroi.composewall.utils

/**
 * Constants used throughout the app.
 */
const val DATABASE_NAME = "threedimens-db"
const val PC_USER_AGENT =
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36"

const val VIEW_POSITION = "position"
const val VIEW_PAGE = "page"
const val ARG_API_TYPE = "api_type"
const val ARG_TITLE = "title"

const val PRELOAD_COUNT = 10

// 每次加载数量
const val PAGE_SIZE_FROM_NET = 20

// 加载重试次数
const val LOAD_LIST_RETRY_TIMES = 3
const val LOAD_PICTURE_RETRY_TIMES = 3

// 加载图片超时
const val LOAD_LIST_TIMEOUT = 5000
const val LOAD_ORIGINAL_TIMEOUT = 10000

// 延迟隐藏加载提示
const val HIDE_LOAD_HINT_DELAY = 150L

