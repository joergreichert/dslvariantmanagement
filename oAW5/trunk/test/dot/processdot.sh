
	    mkdir -p .
		# Please make sure that 'dot' can be found on PATH
		
			echo processing examplevis.dot ...
			%GRAPHVIZ_BIN%/dot -Tgif examplevis.dot -o ./examplevis.gif
		
	