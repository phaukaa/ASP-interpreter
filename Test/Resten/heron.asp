# Using Heron's method of computing the square root.
# Ref: http://en.wikipedia.org/wiki/Methods_of_computing_square_roots
# --- 

def pow (a, b):
  if b > 1:
    return pow(a,b-1) * a
  elif b < 1:
    return pow(a,b+1) / a
  else:
    return a
  

def rough_log (x):
  if x > 10:
    return rough_log(x/10) + 1
  if x < 0.1:
    return rough_log(x*10) - 1
  return 1


def sqrt (v):
  x = [0] * 12

  d = rough_log(v)
  if d%2 == 1:
    x[0] = pow(2*10, (d-1)//2)
  else:
    x[0] = pow(6*10, (d-2)//2)

  for ix in range(0,11):
    x[ix+1] = (x[ix] + v/x[ix])/2

  return x[11]


# The main program:
v = float(input("Give a number: "))
sq2 = sqrt(v)
print(sq2, "squared is", pow(sq2,2))
