package com.danteandroi.composewall.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

/**
 * @author Dante
 * 2022/8/6
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsDialog(
    modifier: Modifier = Modifier,
    optionsArray: Array<Pair<ImageVector, Int>>,
    onDismissRequest: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        content = {
            Spacer(modifier = Modifier.size(12.dp))
            optionsArray.forEachIndexed { index, res ->
                Row(
                    Modifier
                        .clickable {
                            onItemClick.invoke(index)
                            onDismissRequest.invoke()
                        }
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        imageVector = res.first, contentDescription = "Option $index"
                    )
                    Spacer(
                        modifier = Modifier
                            .width(12.dp)
                            .alignByBaseline()
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(id = res.second),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
        })

}