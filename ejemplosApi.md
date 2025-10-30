## ejemplo put y post correcto 

{
  "name": "Goku Super Saiyan",
  "price": 29.99,
  "cantidad": 15,
  "imagen": "https://example.com/images/goku-funko.jpg",
  "categoria": "ANIME"
}
## ejemplo incorrecto para put y post
{
"name": "Batman Classic",
"price": 25.5,
"cantidad": 10,
"imagen": "https://example.com/images/batman-funko.jpg",
"categoria": "COMICS"
}

## ejemplo valido para patch
{
"name": "Goku Ultra Instinto",
"price": 34.99,
"cantidad": 20,
"imagen": "https://example.com/images/goku-ultra.jpg",
"categoria": "ANIME"
}
## ejemplo incompleto para patch
{
"price": 31.5,
"cantidad": 25
"categoria": "ANIME"
}
## ejemplo incorrecto patch
{
"price": -5.0,
"cantidad": 3
}
