ALTER TABLE PROCESSES 
ADD (ORIGINAL_PROCESS_BUNDLE_ID INTEGER NOT NULL);

ALTER TABLE PROCESSES 
ADD (INSTRUMENTED_PROCESS_BUNDLE_ID INTEGER );

ALTER TABLE PROCESSES
ADD CONSTRAINT PROCESSES_PROCESS_BUNDLE_FK1 FOREIGN KEY
(
  ORIGINAL_PROCESS_BUNDLE_ID 
)
REFERENCES PROCESS_BUNDLES
(
  PROCESS_BUNDLE_ID 
)
ENABLE;

ALTER TABLE PROCESSES
ADD CONSTRAINT PROCESSES_PROCESS_BUNDLE_FK2 FOREIGN KEY
(
  INSTRUMENTED_PROCESS_BUNDLE_ID 
)
REFERENCES PROCESS_BUNDLES
(
  PROCESS_BUNDLE_ID 
)
ENABLE;
