package com.danteandroi.composewall.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danteandroi.composewall.R

/**
 * @author Dante
 * 2022/7/29
 */
@Composable
fun BackdropTitle(
    modifier: Modifier = Modifier.height(38.dp),
    title: String = stringResource(id = R.string.app_name),
    onTitleClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
        .clickable {
            onTitleClick.invoke()
        }, verticalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview(showBackground = false)
@Composable
fun AppTitlePreview() {
    BackdropTitle()
}