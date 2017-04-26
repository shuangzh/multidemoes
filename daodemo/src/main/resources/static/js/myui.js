/**
 * myui.js 对easyui进行扩展
 * author: 周双
 * email:zhoushuang@chinamobilesz.com
 * 
 */

// 判断是否是数组类型
function isArray(o) {
	return Object.prototype.toString.call(o) === '[object Array]';
}

$(function() {

	var g_dgextMap = {}

	/**
	 * 在 easyui Datagrid 内部调用，获取table id;target为 调用者的$(this)
	 * @param {Object} target
	 */
	function getTableId(target) {
		var tag = target.prop('tagName')
		var id = null
		if(tag.toLowerCase() == 'table') {
			id = target.attr('id')
		} else {
			var easydg = target.parents('.datagrid-wrap').find('.datagrid-f').first()
			id = easydg.attr('id')
		}
		return id
	}

	function getTableInfo(target) {
		var id = getTableId(target)
		return g_dgextMap[id]
	}

	function checkOp(info, opString) {
		if(info.editIndex != undefined) {
			if(info.action == 'inserted' || info.action == 'updated') {
				$(info.sid).datagrid('selectRow', info.editIndex)
			} else {
				$(info.sid).datagrid('unselectAll')
			}
			console.info("Row Index [" + info.editIndex + "] is edit now, can't do [" + opString + "] action")
			return false
		}
		return true
	}

	var defToolbar = [{
		iconCls: 'icon-add',
		text: 'New',
		handler: function() {
			var info = getTableInfo($(this))
			if(!checkOp(info, 'ADD')) return
			// add action
			$(info.sid).datagrid('appendRow', info.defRowValue)
			info.editIndex = $(info.sid).datagrid('getRows').length - 1
			$(info.sid).datagrid('selectRow', info.editIndex).datagrid('beginEdit', info.editIndex)
			info.action = 'inserted'
		}
	}, {
		iconCls: 'icon-edit',
		text: 'Edit',
		handler: function() {
			var info = getTableInfo($(this))
			if(!checkOp(info, 'EDIT')) return
			var row = $(info.sid).datagrid('getSelected')
			info.editIndex = $(info.sid).datagrid('getRowIndex', row)
			$(info.sid).datagrid('selectRow', info.editIndex).datagrid('beginEdit', info.editIndex)
			info.action = 'updated'
		}
	}, {
		iconCls: 'icon-remove',
		text: 'Remove',
		handler: function() {
			var info = getTableInfo($(this))
			if(!checkOp(info, 'REMOVE')) return
			var row = $(info.sid).datagrid('getSelected')
			info.editIndex = $(info.sid).datagrid('getRowIndex', row)
			$(info.sid).datagrid('deleteRow', info.editIndex)
			info.action = 'deleted'
		}
	}, '-', {
		iconCls: 'icon-save',
		text: 'Accept',
		handler: function() {
			var info = getTableInfo($(this))
			if(info.editIndex != undefined) {
				$(info.sid).datagrid('endEdit', info.editIndex)
				var rowchanges = $(info.sid).datagrid('getChanges')
				if(rowchanges.length > 0) {
					var act = info.action
					var url = null
					if(act == 'inserted') {
						url = info.insertUrl
					} else if(act == 'updated') {
						url = info.updateUrl
					} else if(act == 'deleted') {
						url = info.deleteUrl
					}

					if(url == null || url == undefined) {
						$.messager.alert('ERROR', act + ' to server failed, url is wrong. all changes rollback', 'error')
						$(info.sid).datagrid('rejectChanges')
						info.editIndex = undefined
						info.action = undefined
					}

					console.info('try [' + act + '] :' + JSON.stringify(row) + ' @@url:' + url)
					var row = rowchanges[0]
					$.get(url, row, function(data, status) {
						if(status == 'success') {
							if(act == 'inserted' || act == 'updated') {
								$(info.sid).datagrid('updateRow', {
									index: info.editIndex,
									row: data
								})
							}
							console.info("operation [" + act + "] on server success @@" + url)
							$(info.sid).datagrid('acceptChanges')
						} else {
							$.messager.alert('ERROR', act + ' to server failed, server has exception. all changes rollback @@' + url, 'error')
							$(info.sid).datagrid('rejectChanges')
						}
						info.editIndex = undefined
						info.action = undefined
					})
				}
			}
		}
	}, {
		iconCls: 'icon-undo',
		text: 'Reject',
		handler: function() {
			var info = getTableInfo($(this))
			if(info.editIndex != undefined) {
				if(info.action == 'inserted' || info.action == 'updated') {
					$(info.sid).datagrid('cancelEdit', info.editIndex)
				}
				$(info.sid).datagrid('rejectChanges')
				info.editIndex = undefined
				info.action = undefined
			}
		}
	}, {
		iconCls: 'icon-search',
		text: 'GetChanges',
		handler: function() {
			var info = getTableInfo($(this))
			if(info.editIndex != undefined) {
				$(info.sid).datagrid('endEdit', info.editIndex)
				$(info.sid).datagrid('beginEdit', info.editIndex)
			}
			var rowchanges = $(info.sid).datagrid('getChanges')
			if(rowchanges.length > 0) {
				var act = info.action
				alert(act + " " + JSON.stringify(rowchanges))
			}
		}
	}]

	var onclickrow = function(index, row) {
		var info = getTableInfo($(this))
		console.info('select [' + index + '] row : ' + JSON.stringify(row))
		if(info.editIndex != undefined) {
			if(info.action == 'inserted' || info.action == 'updated') {
				$(info.sid).datagrid('selectRow', info.editIndex)
			} else {
				$(info.sid).datagrid('unselectAll')
			}
			info.preSelected = info.editIndex
			return
		}
		// 级联操作
		if(index != info.preSelected) {
			var childs = info.childDataGrids
			if(childs != null && childs != undefined) {
				for(var t in childs) {
					console.info('try to flash table [' + t + '] with row :' + JSON.stringify(row))
					$('#' + childs[t]).datagrid('load', row)
				}
			}
		}
		info.preSelected = index
	}

	var defParams = {
		rownumbers: true,
		singleSelect: true,
		toolbar: defToolbar,
		onClickRow: onclickrow,
		defRowValue: {},
		fitColumns: true
	}

	$.fn.mydg = function(param) {
		var tableid = $(this).attr('id')
		console.info("trans table [" + tableid + "] to datagrid ......")
		var info = $.extend(false, g_dgextMap[tableid], defParams, param);
		info.id = tableid;
		info.sid = "#" + tableid;
		g_dgextMap[tableid] = info
		$(info.sid).datagrid(info)
	}

	$.fn.dgext = function(param) {
		var tableid = $(this).attr('id')
		console.info("trans table [" + tableid + "] to datagrid")
		var tbinfo = {}
		tbinfo.id = tableid
		tbinfo.defValue = param.defValue
		tbinfo.insertUrl = param.insertUrl
		tbinfo.deleteUrl = param.deleteUrl
		tbinfo.updateUrl = param.updateUrl

		tbinfo.childDataGrids = param.childDataGrids

		g_dgextMap[tableid] = tbinfo
		$(this).datagrid({
			columns: param.columns,
			rownumbers: true,
			singleSelect: true,
			data: param.data,
			url: param.url,
			method: param.method,

			toolbar: [{
				iconCls: 'icon-add',
				text: 'New',
				handler: function() {
					var easydg = $(this).parents('.datagrid-wrap').find('.datagrid-f').first()
					var tbid = easydg.attr('id')
					var editIndex = g_dgextMap[tbid].editIndex
					if(editIndex != undefined) {
						easydg.datagrid('selectRow', editIndex)
						console.info("Row Index [" + editIndex + "] is edit now, can't add new record")
						return
					} else if(editIndex == undefined) {
						if(g_dgextMap[tbid].defValue != undefined) {
							easydg.datagrid('appendRow', g_dgextMap[tbid].defValue)
						} else {
							easydg.datagrid('appendRow', {})
						}
						editIndex = easydg.datagrid('getRows').length - 1
						easydg.datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex)
						g_dgextMap[tbid].editIndex = editIndex
						g_dgextMap[tbid].action = 'inserted'
					}
				}
			}, {
				iconCls: 'icon-edit',
				text: 'Edit',
				handler: function() {
					var easydg = $(this).parents('.datagrid-wrap').find('.datagrid-f').first()
					var tbid = easydg.attr('id')
					var editIndex = g_dgextMap[tbid].editIndex
					if(editIndex != undefined) {
						easydg.datagrid('selectRow', editIndex)
						console.info("Row Index [" + editIndex + "] is edit now, can't edit other row")
						return
					}
					var row = easydg.datagrid('getSelected')
					editIndex = easydg.datagrid('getRowIndex', row)
					easydg.datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex)
					g_dgextMap[tbid].editIndex = editIndex
					g_dgextMap[tbid].action = 'updated'
				}
			}, {
				iconCls: 'icon-remove',
				text: 'Remove',
				handler: function() {
					var easydg = $(this).parents('.datagrid-wrap').find('.datagrid-f').first()
					var tbid = easydg.attr('id')
					var editIndex = g_dgextMap[tbid].editIndex
					if(editIndex != undefined) {
						easydg.datagrid('selectRow', editIndex)
						console.info("Row Index [" + editIndex + "] is edit now, can't remove other row")
						return
					}
					var row = easydg.datagrid('getSelected')
					editIndex = easydg.datagrid('getRowIndex', row)
					easydg.datagrid('deleteRow', editIndex)
					g_dgextMap[tbid].editIndex = editIndex
					g_dgextMap[tbid].action = 'deleted'
				}
			}, '-', {
				iconCls: 'icon-save',
				text: 'Accept',
				handler: function() {
					var easydg = $(this).parents('.datagrid-wrap').find('.datagrid-f').first()
					var tbid = easydg.attr('id')
					var editIndex = g_dgextMap[tbid].editIndex
					if(editIndex != undefined) {
						easydg.datagrid('endEdit', editIndex)
						var rowchanges = easydg.datagrid('getChanges')
						if(rowchanges.length > 0) {
							var act = g_dgextMap[tbid].action
							var url = null
							if(act == 'inserted') {
								url = g_dgextMap[tbid].insertUrl
							} else if(act == 'updated') {
								url = g_dgextMap[tbid].updateUrl
							} else if(act == 'deleted') {
								url = g_dgextMap[tbid].deleteUrl
							}
							if(url != null && url != undefined) {
								var row = rowchanges[0]
								console.info('try [' + act + '] :' + JSON.stringify(row) + ' @@url:' + url)
								$.get(url, row, function(data, status) {
									if(status == 'success') {
										g_dgextMap[tbid].editIndex = undefined
										if(act == 'inserted' || act == 'updated') {
											easydg.datagrid('updateRow', {
												index: editIndex,
												row: data
											})
										}
										easydg.datagrid('acceptChanges')
										return
									} else {
										$.messager.alert('ERROR', act + ' to server failed, server has exception. all changes rollback', 'error')
										easydg.datagrid('rejectChanges')
										g_dgextMap[tbid].editIndex = undefined
										g_dgextMap[tbid].action = undefined
									}
								})
							} else {
								$.messager.alert('ERROR', act + ' to server failed, url is wrong. all changes rollback', 'error')
								easydg.datagrid('rejectChanges')
								g_dgextMap[tbid].editIndex = undefined
								g_dgextMap[tbid].action = undefined
							}
						}
					}
				}
			}, {
				iconCls: 'icon-undo',
				text: 'Reject',
				handler: function() {
					var easydg = $(this).parents('.datagrid-wrap').find('.datagrid-f').first()
					var tbid = easydg.attr('id')
					var editIndex = g_dgextMap[tbid].editIndex
					if(editIndex != undefined) {
						if(g_dgextMap[tbid].action == 'inserted' || g_dgextMap[tbid].action == 'updated') {
							easydg.datagrid('cancelEdit', editIndex)
						}
						easydg.datagrid('rejectChanges')
						g_dgextMap[tbid].editIndex = undefined
						g_dgextMap[tbid].action = undefined
					}
				}
			}, {
				iconCls: 'icon-search',
				text: 'GetChanges',
				handler: function() {
					var easydg = $(this).parents('.datagrid-wrap').find('.datagrid-f').first()
					var tbid = easydg.attr('id')
					var editIndex = g_dgextMap[tbid].editIndex
					if(editIndex != undefined) {
						easydg.datagrid('endEdit', editIndex)
						easydg.datagrid('beginEdit', editIndex)
					}
					var rowchanges = easydg.datagrid('getChanges')
					if(rowchanges.length > 0) {
						var act = g_dgextMap[tbid].action
						alert(act + " " + JSON.stringify(rowchanges))
					}
				}
			}],
			onClickRow: function(index, row) {
				var tbid = $(this).attr('id')
				var editIndex = g_dgextMap[tbid].editIndex
				if(editIndex != undefined) {
					$(this).datagrid('selectRow', editIndex)
					return;
				}
				// 级联操作
				var childs = g_dgextMap[tbid].childDataGrids
				if(childs != null && childs != undefined) {
					for(var t in childs) {
						$('#' + childs[t]).datagrid('load', row)
					}
				}
			}
		})
	}

	// 对easyui form 进行扩展
	g_myformMap = {}

	// form 中的submit按钮
	var defFormSubtn = {
		text: 'Submit',
		onClick: function() {
			var form = $(this).parents('form').first()
			var formid = form.attr('id')
			console.info("submit form id=" + formid)
			$("#" + formid).submit()
		}
	}

	var defForm = {
		orientate: 'vertical',
		onSubmit: function(param) {
			var id = $(this).attr("id")
			console.info("onSubmit [" + id + " ]")
		},
		success: function(data) {
			var id = $(this).attr("id")
			console.info("success submit form [" + id + " ]")
			var updateTable = g_myformMap[id].updateTable
			data = JSON.parse(data)
			if(updateTable != null && updateTable != undefined) {
				console.info("update table [" + updateTable + "]")
				$("#" + updateTable).datagrid('loadData', data)
			}
		}
	}
	
	// 处理不同的 input类型
	function handleInputUI(f, sid) {
		var ui = f.ui
		switch(ui) {
			case 'textbox':
				$(sid).textbox(f)
				break;;
			case 'passwordbox':
				$(sid).passwordbox(f)
				break;;
			case 'numberbox ':
				$(sid).numberbox(f)
				break;;
			case 'datebox':
				$(sid).datebox(f)
				break;;
			case 'datetimebox':
				$(sid).datetimebox(f)
				break;;
			case 'combobox':
				$(sid).combobox(f)
				break;;
			case 'timespinner':
				$(sid).timespinner(f)
				break;;
			default:
				$(sid).textbox(f)
		}
	}

	/**
	 * $('#form-id').myform({..}) 
	 * @param {Object} param
	 */
	
	$.fn.myform = function(param) {
		var formid = $(this).attr('id')
		console.info("trans form [" + formid + "] to myform ......")
		var info = $.extend(false, g_myformMap[formid], defForm, param)
		info.sid = "#" + formid
		g_myformMap[formid] = info

		var fields = info.fields
		var intab = $("<table id='" + formid + "_tb'></table>")
		$(this).append(intab)

		//生成垂直的表单
		if(info.orientate == 'vertical') {
			for(x in fields) {
				var f = fields[x]
				var tr = $("<tr></tr>")
				var td = $("<td></td>")
				var inp = $("<input id='" + formid + "_" + f.name + "' name='" + f.name + "'></input>")
				var sid = "#" + formid + "_" + f.name
				td.append(inp)
				tr.append(td)
				intab.append(tr)
				handleInputUI(f, sid)
			}
			var btr = $("<tr></tr>")
			var btd = $("<td></td>")
			var btn = $("<a id='" + formid + "_sbtn'></a>")
			btd.append(btn)
			btr.append(btd)
			intab.append(btr)
			var btnparam = $.extend(false, defFormSubtn, param.formSubtn);
			$("#" + formid + "_sbtn").linkbutton(btnparam)

		} else if(info.orientate == 'horizontal') {
			var tr = $("<tr></tr>")
			intab.append(tr)
			for(x in fields) {
				var f = fields[x]
				var td = $("<td></td>")
				var inp = $("<input id='" + formid + "_" + f.name + "' name='" + f.name + "'></input>")
				var sid = "#" + formid + "_" + f.name
				td.append(inp)
				tr.append(td)
				handleInputUI(f, sid)
			}
			var btd = $("<td></td>")
			var btn = $("<a id='" + formid + "_sbtn'></a>")
			btd.append(btn)
			tr.append(btd)
			var btnparam = $.extend(false, defFormSubtn, info.formSubtn);
			$("#" + formid + "_sbtn").linkbutton(btnparam)
		}
		$(this).form(info)
	}
	
	//	var g_myAccordingMap={}
	//	$.fn.myAccording=function(param){
	//		var id=$(this).attr('id');
	//		var info=$.extend(true, g_myAccordingMap[id], param);
	//		g_myAccordingMap[id] = info
	//		
	//		var panels=info.panels;
	//		for(i in panels)
	//		{
	//			var p = panels[i]
	//			var div= $("<div title='"+p.title+"' style='padding:10px;'></div>")
	//			var tabbtns=p.tabbtns
	//			if(tabbtns !=null && tabbtns !=undefined && isArray(tabbtns) && tabbtns.length>0)
	//			{
	//				
	//			}
	//		}
	//	}
	
	
	/**
	 *  use easy ui to navigate 
	 */
	var g_myNavigateTabsId = null

	var defNaviParam = {
		onClick: function(node) {
			console.info('navigate tree [' + node.text + '] is clicked')
			if(node.url != undefined && node.url.trim().length > 0) {
				console.info('try to open url [' + node.url + ']')
				var cont = '<iframe scrolling="auto" frameborder="0" style="width:100%;height: 90%;" src="' + node.url + '" > </iframe>'
				$("#"+g_myNavigateTabsId).tabs('add', {
					title: node.text,
					closable: true,
					content: cont
				})
			}

		}
	}

	function navigeteNode(nodes, level) {
		for(x in nodes) {
			var node = nodes[x]
			if(node.iconCls == undefined) {
				if(level == 1) {
					node.iconCls = 'icon-basic'
				} else {
					node.iconCls = 'icon-gears'
				}
			}
			var child = node.children
			if(child != undefined && child != null && isArray(child)) {
				navigeteNode(child, level + 1)
			}
		}
	}

	$.fn.mynavigate=function(param) {
		g_myNavigateTabsId = param.tabsid
		navigeteNode(param.data, 1)
		var pa=$.extend(true, defNaviParam, param);
		$(this).tree(pa)
	}
})