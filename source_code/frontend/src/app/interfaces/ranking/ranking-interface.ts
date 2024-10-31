/**
 * Ranking Interface
 * 
 * @author Javier Huang
 */

export interface IRankingBaseInfo {
    sid: number,
    studentId: number,
    firstName: string,
    lastName: string,
    name: string,
    grade: number,
    pointsEarned: number,
    isWinner: boolean,
    isRandom: boolean,
    prizeName: string,
    prizeDescription: string,
    schoolYear: string,
    quarter: number
}

export interface IRankingSearch {
    searchType: string,
    schoolYear: string,
    quarter: number,
    firstName?: string,
    lastName?: string,
    grade?: number,
    pointsEarned?: number
}

export interface IGrantPrizes {
    schoolYear: string,
    quarter: number
}

export enum RANKING_SELECT_OPTIONS {
    ALL = '0',
    NAME = '1',
    GRADE = '3',
    POINTS_EARNED = '4'
}