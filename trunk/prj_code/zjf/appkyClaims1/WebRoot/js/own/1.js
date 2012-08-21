Ext.onReady(function() {	
			Ext.MessageBox.show({
						msg : 'Loading items...',
						progressText : 'Initializing...',
						width : 300,
						progress : true,
						closable : false,
						animEl : 'mb6'
					});

			// this hideous block creates the bogus progress
			var f = function(v) {
				return function() {
					if (v == 12) {	
						Ext.MessageBox.hide();
						
					} else {
						var i = v / 11;
						Ext.MessageBox.updateProgress(i, Math.round(100 * i)
										+ '% completed');
					}
				};
			};
			for (var i = 1; i < 13; i++) {
				setTimeout(f(i), i * 500);
			}
		});