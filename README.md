
# Paper Company
*Goal:* Create a Customer API that would help to power an online paper store (Dunder Mifflin Paper
Company). Your API should be able to Create, View, List, and Delete customers.

Customers can also be updated.

## Features

- All customers can be requested
- Single customer can be requested
- Customers can be created
- Customers can be updated
- Customers can be deleted

## Scaling

See [scaling.md](scaling.md)

## Run Locally

Clone the project

```bash
  git clone https://github.com/theappreciator/papercompany.git
```

Go to the project directory

```bash
  cd papercompany
```

Install dependencies

```bash
  mvn install
```

Start the server

```bash
  mvn spring-boot:run
```

## Running Tests

To run tests, run the following command

```bash
  mvn test
```

#### Current unit test coverage:
| Class      | Method      | Line        |
| :--------- | :---------- | :---------- |
| 100% (8/8) | 95% (40/42) | 93% (86/92) |

## API Reference

#### Get all customers

```http
  GET /customer
```

HTTP Response

| Code | Definition |
| :--- | :--------- |
| 200  | Success    |

Example response:
```json
[
    {
        "id": "279f504a-ff15-495f-ab4c-f80bd2e8f81b",
        "version": 0,
        "lastName": "Halpert",
        "firstName": "Jim",
        "emailAddress": "jim.h@papercompany.com"
    },
    {
        "id": "1b752a82-ad73-44fe-a4b9-60651be34790",
        "version": 0,
        "lastName": "Beesly",
        "firstName": "Pam",
        "emailAddress": "pam.b@papercompany.com"
    },
    {
        "id": "2467420e-87c3-4738-97f9-9fe470d8e90f",
        "version": 0,
        "lastName": "Schrute",
        "firstName": "Dwight",
        "emailAddress": "dwight.s@papercompany.com"
    }
]
```

#### Get single customer

```http
  GET /customer/{id}
```

| URL Parameter | Type   | Description                            |
| :------------ | :----- | :------------------------------------- |
| `id`          | `uuid` | **Required**. Id of customer to update |

HTTP Response

| Code | Definition               |
| :--- | :----------------------- |
| 200  | Successfully found       |
| 404  | Customer not found       |

Example response:
```json
[
    {
        "id": "279f504a-ff15-495f-ab4c-f80bd2e8f81b",
        "version": 0,
        "lastName": "Halpert",
        "firstName": "Jim",
        "emailAddress": "jim.h@papercompany.com"
    }
]
```

#### Create customer

```http
  POST /customer/
```

| Body Parameter     | Type     | Description                    |
| :----------------- | :------- | :----------------------------- |
| `lastName`         | `string` | Last name of the customer      |
| `firstName`        | `string` | First name of the customer     |
| `emailAddress`     | `string` | Email Address of the customer  |

HTTP Response

| Code | Definition                 |
| :--- | :------------------------- |
| 200  | Successfully created       |

Example request body:
```json
[
    {
        "lastName": "Halpert",
        "firstName": "Jim",
        "emailAddress": "jim.h@papercompany.com"
    }
]
```
Example response:
```json
[
    {
        "id": "279f504a-ff15-495f-ab4c-f80bd2e8f81b",
        "version": 0,
        "lastName": "Halpert",
        "firstName": "Jim",
        "emailAddress": "jim.h@papercompany.com"
    }
]
```

#### Update customer

```http
  PUT /customer/{id}
```

| URL Parameter | Type   | Description                            |
| :------------ | :----- | :------------------------------------- |
| `id`          | `uuid` | **Required**. Id of customer to update |

| Body Parameter     | Type     | Description                    |
| :----------------- | :------- | :----------------------------- |
| `lastName`         | `string` | Last name of the customer      |
| `firstName`        | `string` | First name of the customer     |
| `emailAddress`     | `string` | Email address of the customer  |

HTTP Response

| Code | Definition                 |
| :--- | :------------------------- |
| 200  | Successfully updated       |
| 404  | Customer not found         |

Example request body:
```json
[
    {
        "emailAddress": "jim.halpert@papercompany.com"
    }
]
```
Example response:
```json
[
    {
        "id": "279f504a-ff15-495f-ab4c-f80bd2e8f81b",
        "version": 1,
        "lastName": "Halpert",
        "firstName": "Jim",
        "emailAddress": "jim.halpert@papercompany.com"
    }
]
```

#### Delete customer

```http
  DELETE /customer/{id}
```

| URL Parameter | Type   | Description                            |
| :------------ | :----- | :------------------------------------- |
| `id`          | `uuid` | **Required**. Id of customer to update |

HTTP Response

| Code | Definition                 |
| :--- | :------------------------- |
| 200  | Successfully deleted       |
| 404  | Customer not found         |

## Authors

- Jess Tucker [@theappreciator](https://www.github.com/theappreciator)

  