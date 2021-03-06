# OODAE2

---

<h1>Use Case Diagram</h1>

![Untitled Diagram](https://user-images.githubusercontent.com/71900386/148433320-811668a1-4f49-4dc8-b5ff-c24ea88dea0a.jpg)

<h1>JavaDoc</h1>
<p>To generate a Javadoc, right click on the desired module and click "Generate Javadoc"  </p>

![Capturejavadoc](https://user-images.githubusercontent.com/71900386/148467254-537ac0ad-46e5-4a05-a2bf-98b3a4b55b40.PNG)

<h2>JavaDoc for MVCcontroller</h2>

![mvcontrollerjavadoc](https://user-images.githubusercontent.com/71900386/148692476-fc97687f-80ae-4445-956d-9f184be8d300.PNG)

<h1>Robust Diagram</h1>

![robust diagram ae2](https://user-images.githubusercontent.com/71900386/148481968-7f42266e-137e-4a02-ba33-21aefef51168.png)

<h1>UML Web Model</h1>

![shoppingCartApplicationWebDiagram](https://user-images.githubusercontent.com/71900386/148483820-a4f7207f-165f-4169-85cf-564230d02197.png)




<h2>Test 1:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Valid or invalid           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Catalog Database loading | Catalog database is loaded with three pre populated items | The catalog is populated with three items which the user can add to their basket | Valid ![image](https://user-images.githubusercontent.com/71900386/148693312-f354f955-52c7-40b2-ba6f-26a8ccffa619.png) |     none      |

<br>

<h2>Test 2:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Valid or invalid           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Adding items to basket | The basket can be populated with items from the catalog and stack items using the quantity variable | The basket is appropiately populated with the chosen items and can be stacked which increases the total | Valid ![image](https://user-images.githubusercontent.com/71900386/148693468-a594cc4b-e355-4f41-a56a-ba071a4bcfdc.png)|     none      |

<br>

<h2>Test 3:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Valid or invalid           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Completing a transaction | Any user can enter card details and send a transaction | The card details are sent to the bank REST API which decrements the total from the card and adds to the banks card | Valid ![image](https://user-images.githubusercontent.com/71900386/148693582-b446b52f-1d36-44e1-bacf-8cb7ce8d6592.png) |     none      |

<br>

<h2>Test 4:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Valid or invalid           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Invalid Transaction | If the user encounters an error when completing a transaction, the application will error | When the user encounters an error a try/catch error function is thrown to prevent the website from crashing | Valid ![image](https://user-images.githubusercontent.com/71900386/148693756-a0a4bde2-ba74-4b3b-bdfc-2623ad92a28f.png) |     none      |

<br>

<h2>Test 5:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Valid or invalid           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Generating Invoice | When a logged in user completes a transaction, an invoice is generated | An invoice is generated and is available to view to the admin | Valid ![image](https://user-images.githubusercontent.com/71900386/148694424-d503c5dd-7173-4af5-9645-a867c39b8747.png) |     none      |

<br>

<h2>Test 6:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Valid or invalid           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Setting Properties | An admin can login and set properties to decide the link to the bank REST API and the credit card to send money to | The logged in admin can set properties | Valid ![image](https://user-images.githubusercontent.com/71900386/148694606-6da369de-c2f5-47b5-81dd-fc71ecdbe74a.png) |     none      |

<br>

<h2>Test 7:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Valid or invalid           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Adding an item | An admin can create an item | The logged in admin can enter details for an item and add it to the catalog  | Valid ![image](https://user-images.githubusercontent.com/71900386/148694762-5a51825a-f404-4ac4-a3bc-f9ad0cc192cb.png) |     none      |

<br>

<h2>Test 8:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Valid or invalid           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Modifying an item | An admin can modify an item | The logged in admin can change details for an item in the catalog  | Valid ![image](https://user-images.githubusercontent.com/71900386/148694896-cfcd1966-0218-4900-8c0d-f9d688e21f76.png) |     none      |

<br>

<h2>Test 9:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Valid or invalid           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Removing an item | An admin can remove an item | The logged in admin can remove an item in the catalog  | Valid ![image](https://user-images.githubusercontent.com/71900386/148694948-1c1f73cd-9339-4830-b008-f9d8b54dc769.png) |     none      |

<br>

<h2>Test 10:</h2>

|          Test           |                            Expected Result                             |                            Actual Result                             |          Valid or invalid           | Action needed |
| :---------------------: | :--------------------------------------------------------------------: | :------------------------------------------------------------------: | :---------------------------------: | :-----------: |
| Transaction Logging | Transactions are to be logged to a file | A file with logged transactions can be viewed in the tomcat directory | Valid ![image](https://user-images.githubusercontent.com/71900386/148695233-ab32d286-1e4c-400d-a5f4-ae13e0cf0da2.png) |     none      |

<br>


