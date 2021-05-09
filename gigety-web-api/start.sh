#!/bin/bash
gradle --version
gradle build -x test --continuous &
gradle bootRun
