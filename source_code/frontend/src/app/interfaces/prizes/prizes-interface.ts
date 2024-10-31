/**
 * Prizes Interface
 * 
 * @author Javier Huang
 */

export interface IPrizesBaseInfo {
    pid: number,
    name: string,
    description: string,
    type: string,
    min_points: number
}

export interface IPrizesSearch {
    schoolYear: string,
    quarter: number
}