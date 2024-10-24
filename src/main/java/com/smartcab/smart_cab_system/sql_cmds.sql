CREATE TABLE Users (
    UserId SERIAL PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(100) NOT NULL
);


CREATE TABLE Verification_codes (
    Email VARCHAR(100) NOT NULL,
    OTP VARCHAR(6) NOT NULL,
    TimeStamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (Email) REFERENCES Users(Email) ON DELETE CASCADE
);


CREATE TABLE Cab_Details (
    CabId SERIAL PRIMARY KEY,
    VehicleNumber VARCHAR(20) NOT NULL UNIQUE,
    DriverName VARCHAR(100) NOT NULL,
    DriverNumber VARCHAR(15) NOT NULL,
    CabModel VARCHAR(50),
    Capacity INT NOT NULL
);


CREATE TABLE Cab_Status (
    CabId INT NOT NULL,
    Location VARCHAR(100) NOT NULL,
    Region VARCHAR(100) NOT NULL,
    CurrentCapacity INT,
    Status VARCHAR(20) NOT NULL,
    PRIMARY KEY (CabId),
    FOREIGN KEY (CabId) REFERENCES Cab_Details(CabId) ON DELETE CASCADE
);


CREATE TABLE Trip (
    TripId SERIAL PRIMARY KEY,
    CabId INT NOT NULL,
    UserId INT NOT NULL,
    Source VARCHAR(100) NOT NULL,
    Destination VARCHAR(100) NOT NULL,
    NoOfPassengers INT NOT NULL,
    FOREIGN KEY (CabId) REFERENCES Cab_Details(CabId) ON DELETE CASCADE,
    FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE
);






-- 1. Create a User
-- 1.1 Insert into Users Table
INSERT INTO Users (Name, Email, Password)
VALUES ('John Doe', 'john.doe@example.com', 'hashed_password');

-- 1.2 Insert into Verification_codes Table
INSERT INTO Verification_codes (Email, OTP, CurrentTimeStamp)
VALUES ('john.doe@example.com', '123456', CURRENT_TIMESTAMP);

-- 1.3 Select from Verification_codes for verification
SELECT OTP FROM Verification_codes 
WHERE Email = 'john.doe@example.com' 
  AND CurrentTimeStamp > (NOW() - INTERVAL '15 minutes');

-- 2. Login User
-- Select Users to Validate Login
SELECT UserId, Name, Email 
FROM Users 
WHERE Email = 'john.doe@example.com' AND Password = 'hashed_password';

-- 3. Cab Booking
-- 3.1 Free Cabs
-- 3.1.1. Select Free Cabs from Cab_Status Based on Location
SELECT Cab_Status.CabId, Location, CurrentCapacity 
FROM Cab_Status 
WHERE Region = 'desired_region' 
  AND Status = 'free'
ORDER BY Location ASC;  -- You can use a proximity algorithm to calculate distance

-- 3.1.2. Choose Cab and Update Status to "Engaged"
UPDATE Cab_Status
SET Status = 'engaged', CurrentCapacity = 0
WHERE CabId = 1;

-- 3.1.3. Book the Cab and Create a Trip
INSERT INTO Trip (CabId, UserId, Source, Destination, NoOfPassengers)
VALUES (1, 101, 'Current_Location', 'Destination_Location', 3);

--3.1.4. Update Cab Current Capacity and Status after Booking
UPDATE Cab_Status
SET CurrentCapacity = 3, Status = 'booked'
WHERE CabId = 1;

-- 3.1.4. Update Cab Location during Trip real-time
UPDATE Cab_Status
SET Location = 'Current_Location'
WHERE CabId = 1;

-- 3.1.5. Update Cab Status to "Free" after Trip Completion
UPDATE Cab_Status
SET Status = 'free', CurrentCapacity = 3
WHERE CabId = 1;

-- 3.2 Engaged Cabs
-- 3.2.1. Select Cabs Currently Engaged and Current Capacity less than Capacity
SELECT CabId, Location, CurrentCapacity
FROM Cab_Status 
WHERE Region = 'desired_region' 
  AND Status = 'engaged'
  AND CurrentCapacity > 0;

-- 3.2.2. Select Cab Details for Display
SELECT CabId, VehicleNumber, DriverName, DriverNumber
FROM Cab_Details 
WHERE CabId = 1;

