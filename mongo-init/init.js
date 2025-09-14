db = db.getSiblingDB('mydatabase');

// Create an app user (read/write on appdb)
db.createUser({
  user: 'root',
  pwd: 'rootpw',
  roles: [{ role: 'readWrite', db: 'mydatabase' }]
});

db.createCollection('users', {
  validator: {
    $jsonSchema: {
      bsonType: 'object',
      required: ['cpf', 'name'],
      properties: {
        email: { bsonType: 'string', pattern: '^.+@.+\\..+$' },
        name: { bsonType: 'string' },
        createdAt: { bsonType: 'date' }
      }
    }
  }
});

db.customers.createIndex({ cpf: 1 }, { unique: true, name: 'uniq_cpf' });

db.customers.insertOne({ cpf: '1234', name: 'Ana', createdAt: new Date() });
