### Product Endpoints:

- Get all products:                       GET     /api/products
- Get product by ID:                      GET     /api/products/{id}
- Search products:                        GET     /api/products/search?query={keyword}
- Get products by category:               GET     /api/products/category/{category}
- Get available products:                 GET     /api/products/available

### Shopping Cart Endpoints:

- Create new cart:                        POST    /api/cart
- Get cart:                               GET     /api/cart/{cartId}
- Add product to cart:                    POST    /api/cart/{cartId}/items?productId={productId}&quantity={quantity}
- Remove product from cart:               DELETE  /api/cart/{cartId}/items/{productId}
- Empty cart:                             DELETE  /api/cart/{cartId}
- Process payment:                        POST    /api/cart/{cartId}/payment

The shopping cart app supports following product categories:

    - fruits
    - bakes
    - dairy
    - Drinks
    - Clothes
    - phone accessories
    - books
-----------------------------------------------------------------
    -- To work with the shopping cart
    -1 first create a cart through "create new cart" endpoint.
    -2 Then you can add products,
    -3 view cart contents, 
    -4 and process payments.

### database configuration info
spring.datasource.url=jdbc:postgresql://localhost:5432/ShoppingCartDB
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true