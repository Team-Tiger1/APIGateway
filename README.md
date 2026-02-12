# API Gateway

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

This service handles:
- Routing Requests
- Authenticating Requests
- CORS Configuration


The diagram below highlights where in our architecture the API Gateway operates.

<p>
  <img src="Architecture_APIGateway.png" alt="Project Logo" height="500px">
</p>


## Tech Stack

### Core & Build

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-%23C71A36.svg?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

### Deployment
![Kubernetes](https://img.shields.io/badge/kubernetes-%23326ce5.svg?style=for-the-badge&logo=kubernetes&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)


## Contribution

**Author: Daniel Jackson**
- Setup this routing service to manage sending request to the right service, using pattern matching based on the URL.
- Configured CORS so the production website and development environments can send requests to the backend.
- Setup Authentication Security Filter that:
    - Verifies token signature for authenticity
    - Verifies token has not expired
    - Written explicit paths that are exempt from authentication (E.G Endpoint Documentation Website)
    - Used Maven Licensing Plugin to check permissions of dependency licenses (Software Inventory)
- Created README file to show details about the repository

<br>

**Author: Jed Leas**

- Setting up all CI/CD workflows to handle
  1. Automatic testing on push of main branch on the API Gateway repository
  2. Automatic Deployment onto k3s with zero downtime on completion of automatic testing so broken code won't make it to deployment
- Helped Daniel Jackson in the diagnosis and fixing of the CORS Errors and configuration.


