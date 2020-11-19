/*
 * 김호철
 * 
 */
(function(window, $) {
	'use strict';
	
	var listTable = function(options){
		var that = this;
		var opt = options || {};
		
		this.config = $.extend({}, this._defaults, opt);
		
		this.table = undefined;
		
		this.requestData().then(function(result){
			console.log(result);
			that._createTable(result);
		});
	}
	
	listTable.prototype = {
		_defaults: {
			url: '',
			type: 'POST',
			cache: false,
			data: {
				rows : 5,
				page : 1,
			},
			dataType: 'json',
			addCount: 5,
			target: 'body',
			onCellClick:  function(id){}
		},
		_createTable: function(data){
			var that = this;
			this.table = $('<table class="ui very basic table">');
			var html = '';
			html += '<tbody>';
			for(var i in data){
				html += '<tr><td data-id="' + data[i].seq + '">';
				html += '<div class="flex-list">';
				html += '<div class="content"><div class="ui small header">' + data[i].subject + '<div class="sub header">' + data[i].indate + '</div></div></div>';
				html += '<div class="center-aligned right"><i class="large grey chevron right icon"></i></div>';
				html += '</div>';
				html += '</td></tr>';
			}
			html += '</tbody>';
			html += '<tfoot><tr>';
			html += '<th class="center aligned">';
			//html += '<div class="ui pagination menu">';
			//html += '<a class="icon item"><i class="left chevron icon"></i></a>';
			//for(var i = 1; i <= this.config.pageCount; i++){
			//	html += '<a class="item">' + i + '</a>';
			//}
			//html += '<a class="icon item"><i class="right chevron icon"></i></a>';
			//html += '</div>';
			html += '<button class="ui button"><i class="plus icon"></i>더보기</button>';
			html += '</th>';
			html += '</tr></tfoot>';
			this.table.append(html);
			$(this.config.target).append(this.table);
			
			this.table.find('.ui.button').click(function(){
				that.addList();
			});
			
			$(document).on('click', this.config.target + ' td', function(){
				var id = $(this).data('id');
				that.config.onCellClick(id);
			});
		},
		requestData: function(options){
			var config =  $.extend({}, this.config, options || {});
			return new Promise(function(resolve, reject){
				$.ajax({
					type: config.type,
					url	: config.url,
					dataType: config.dataType,
					data: config.data,
					cache: config.cache,
					beforeSend: function(){
					},
					success	: function(result){
						resolve(result.root);
					},
					complete: function(){
					}
				});
			});
		},
		addList: function(){
			var that = this;
			
			this.config.data.page += 1;
			
			this.requestData().then(function(result){
				var tbody = that.table.find('tbody');
				var html;
				for(var i in result){
					html = '';
					html += '<tr><td data-id="' + result[i].seq + '">';
					html += '<div class="flex-list">';
					html += '<div class="content"><div class="ui small header">' + result[i].subject + '<div class="sub header">' + result[i].indate + '</div></div></div>';
					html += '<div class="center-aligned right"><i class="large grey chevron right icon"></i></div>';
					html += '</div>';
					html += '</td></tr>';
					tbody.append(html);
				}
				
				if(result.length < 5){
					that.table.find('.ui.button').remove();
				}
			});
		}
	}
	window.ListTable = listTable;
})(window, jQuery);