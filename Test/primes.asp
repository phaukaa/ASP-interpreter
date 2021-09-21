
# Finn alle primtall opp til n
# ved hjelp av teknikken kalt «Eratosthenes' sil».

n = 1000
primes = [True] * (n+1)

def find_primes():
   for i1 in range(2,n+1):
      i2 = 2 * i1
      while i2 <= n:
         primes[i2] = False
         i2 = i2 + i1

def w4(n):
    if n <= 9:
        return '   ' + str(n)
    elif n <= 99:
        return '  ' + str(n)
    elif n <= 999:
        return ' ' + str(n)
    else:
        return str(n)

def list_primes():
    n_printed = 0
    line_buf = ''
    for i in range(2,n+1):
       if primes[i]:
          if n_printed > 0 and n_printed%10 == 0:
             print(line_buf)
             line_buf = ''
          line_buf = line_buf + w4(i)
          n_printed = n_printed + 1
    print(line_buf)

find_primes()
list_primes()
