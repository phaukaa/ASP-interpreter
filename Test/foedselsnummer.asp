# Dette programmet leser norske fødelsnumre (11 sifre) og
# sjekker om kontrollsifrene er riktige.

vekter_1 = [ 3, 7, 6, 1, 8, 9, 4, 5, 2 ]
vekter_2 = [ 5, 4, 3, 2, 7, 6, 5, 4, 3, 2 ]

def sjekk_kontrollsiffer (n, vekter):
   sum = 0
   for i in range(0,n):
      sum = sum + vekter[i]*sifre[i]
   return (sum+sifre[n])%11 == 0

def kun_sifre (t):
   res = ""
   for i in range(0, len(t)):
      c = t[i]
      if ('0' <= c and c <= '9'):
         res = res + c  # Ta vare på desimale sifre
      else:
         pass  # Ignorer alle andre tegn
   return res

while True:
   fnr = kun_sifre(input("Fødselsnummer: "))
   if fnr == "": exit(0)

   if len(fnr) != 11:
      print(fnr, "har ikke 11 sifre!")
   else:
      sifre = [0] * 11;
      for i in range(0, 11): 
         sifre[i] = int(fnr[i])
      if sjekk_kontrollsiffer(9,vekter_1) and sjekk_kontrollsiffer(10,vekter_2):
         print("Fødselsnummer", fnr, "er OK.")
      else:
         print("Fødselsnummer", fnr, "har feil i kontrollsiffer.")
