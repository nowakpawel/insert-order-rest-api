# REST API for Orders

## Intro
Rest API to manage orders - backend implementation.

## Endpoints

- `[GET][domain]/insertapi/orders` - Get all orders from database.
- `[GET][domain]/insertapi/orders{id}` - Get specific order by id.
- `[POST][domain]/insertapi/add` - Create new order.
- `[PUT][domain]/insertapi/orders{id}/confirm` - Confirm order with specific id.
- `[PUT][domain]/insertapi/orders{id}/cancel` - Cancel order with specific id.
- `[PUT][domain]/insertapi/orders{id}/deliver` - Deliver order with specific id.
