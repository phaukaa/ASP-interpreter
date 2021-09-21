# I Asp får tall ikke lov å starte med sifferet 0, men
# selve tallet 0 er selvfølgelig et unntak.
# (I Python 2 er det anderledes; da brukes innledende 0-siffer
# til å angi at tallet er på oktal form.)

a = 0   # Dette er helt OK.
a = 04  # Men ikke dette. I Asp er dette to tall: 0 og 4!
