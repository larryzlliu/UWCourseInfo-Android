# UWCourseInfo-Android

A work-in-progress Android app that allows the user to search courses from University of Waterloo and get information about it.

## Motivation

Inspired by UWCourseBot, a project started at ENGHack 16 with a group of friends.  
UWCourse Bot Repo: https://github.com/nathanliu1/UWCourseBot

## Usage

Search for any valid courses run by the University of Waterloo.
- Send API requests to fetch related information about the search
- Parses the JSON return into viewable data for the user in the reply
- Some sample inputs:
	* ECON 101
	* CS 246

### TODO's
- () Have a class schedule view in each course view
- () Return a list of related courses after the search
- () Implement better design

## API Reference

All API calls are made towards the University of Waterloo Open Data API.  
See [Documentation](https://github.com/uWaterloo/api-documentation) for more information.