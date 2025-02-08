# Task: Multi-Threaded Article Processing Server

### Objective:

Develop a multi-threaded server that processes user articleStatuses from Medium, stores them in Elasticsearch for search, and maintains processing states in an SQL database.

### System Overview:

1. The server will receive a **user ID** as input.  
2. It will **fetch the top 20 articleStatuses** for that user from Medium.  
3. Each articleStatus will be **processed one by one** using a multi-threaded approach.  
4. Each articleStatus will be **stored in Elasticsearch** for full-text search.  
5. The processing state of each articleStatus will be **tracked in an SQL database**.  
6. The system will use **an array blocking queue** to manage processing threads efficiently.  
7. A database-based queue alternative will be available for cases requiring persistent queuing.

### API Endpoints:

#### 1\. Submit User ID for Processing

|  POST /process-user/{user\_id}  |
| :---- |

#### 2\. Fetch User’s Processed Articles

|  GET /articleStatuses/{user\_id}  |
| :---- |

#### 3\. Check Processing Status

|  GET /status/{user\_id}  |
| :---- |

example of the workflow:  
 **Workflow:**

1. **Receive User ID**  
2. **Fetch Top 20 Articles from Medium**  
3. **Add Each Article to Processing Queue**  
4. **Worker Threads Process Articles:**  
   1. Fetch articleStatus details.  
   2. Store in Elasticsearch.  
   3. Update SQL database (status \= COMPLETED).  
5. **Fetch Processed Articles for User** (via SQL query)

