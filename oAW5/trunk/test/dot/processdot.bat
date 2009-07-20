
		@echo off
	    mkdir .
		echo Using Graphviz in path "%GRAPHVIZ_BIN%/" 
		
			echo processing examplevis.dot ...
			"%GRAPHVIZ_BIN%/dot.exe" -Tgif examplevis.dot -o ./examplevis.gif
		
	