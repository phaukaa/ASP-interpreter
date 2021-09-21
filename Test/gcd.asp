
# A program to compute the greatest common divisor
# of two numbers, i.e., the biggest number by which
# two numbers can be divided without a remainder.
#
# Example:  gcd(180,48) = 12

def GCD (m, n):
   if n == 0:
      return m
   else:
      return GCD(n, m % n)

v1 = int(input("A number: "))
v2 = int(input("Another number: "))

res = GCD(v1,v2)
print('GCD('+str(v1)+','+str(v2)+') =', res)
