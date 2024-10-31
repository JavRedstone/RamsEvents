/**
 * Events Interface
 * 
 * @author Javier Huang
 */

export interface IEventsBaseInfo {
    eid: number,
    eventName: string,
    description: string,
    maxPoints: number,
    minPoints: number,
    startDate: Date,
    endDate: Date,
    clubName: string,
    schoolYear: string,
    quarter: number
}

export interface IEventsSearch {
    searchType: string,
    schoolYear: string,
    quarter: number,
    eventName?: string,
    minPoints?: number,
    clubName?: string,
    clubLeaderId?: number,
    endDate?: string
}

export interface IEventsJoinedSearch {
    sid: number,
    searchType: string,
    schoolYear: string,
    quarter: number,
    eventName?: string,
    minPoints?: number,
    clubName?: string,
    endDate?: string
}

export interface IEventsActions {
    eid: number,
    sid: number,
    schoolYear: string,
    quarter: number
}

export interface IEventsAdd {
    name: string,
    description: string,
    minPoints: number,
    maxPoints: number,
    startDate: string,
    endDate: string,
    cid: number,
    schoolYear: string,
    quarter: number
}

export interface IEventsUpdate {
    eid: string,
    name: string,
    description: string,
    minPoints: number,
    maxPoints: number,
    startDate: string,
    endDate: string,
    schoolYear: string,
    quarter: number
}

export interface IEventsDelete {
    eid: number
}

export interface IEventsStudents {
    firstName: string,
    lastName: string,
    email: string
}

export enum EVENTS_SELECT_OPTIONS {
    ALL = '0',
    EVENT_NAME = '1',
    MIN_POINTS = '2',
    CLUB_NAME = '3',
    CLUB_LEADER_ID = '4',
    END_DATE = '5'
}