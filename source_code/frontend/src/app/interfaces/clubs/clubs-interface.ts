/**
 * Clubs Interface
 * 
 * @author Javier Huang
 */

export interface IClubsBaseInfo {
    cid: number,
    clubName: number,
    description: number,
    leaderId: number,
    firstName: string,
    lastName: string,
    leaderName: string
    email: string
}

export interface IClubsSearch {
    searchType: string,
    clubName?: string,
    description?: string,
    firstName?: string,
    lastName?: string,
    leaderId?: number
}

export enum CLUBS_SELECT_OPTIONS {
    ALL = '0',
    CLUB_NAME = '1',
    DESCRIPTION = '2',
    LEADER_NAME = '3',
    LEADER_ID = '4'
}