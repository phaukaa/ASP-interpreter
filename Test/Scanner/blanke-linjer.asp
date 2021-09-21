# Dette programmet inneholder ulike former for blanke linjer,
# dvs tomme linjer eller linjer med blanke eller TAB-er i ulike posisjoner.
# Dette skal Asp-interpreten kunne håndtere.

# Denne linjen er tom:

# Denne linjen inneholder kun noen blanke i starten:
    
# Denne linjen starter med en TAB:
	
if 1+1 = 2:
# Denne linjen starter med 2 TAB-er og 3 blanke:
		   print("Ja")
# Denne linjen inneholder ut utplukk blanke og TAB-er:
	 		   	
# Og det var alt.
# Siste linje mangler LF som linjeslutt, men slikt skal skanneren kunne takle.
# Skanneren skal generere et NEWLINE-symbol uansett.
x = 4.
