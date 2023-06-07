
import axios from 'axios';

/**
 * 村民信息
 */
export class VillagerService {
    // 分页查询列表
    static findVillagers (queryParams) {
        return axios({
            url: '/cms/villager/Villager/findVillagers',
            method: 'POST',
            data: queryParams
        });
    }
    //新增保存村民信息
    static saveVillager (villager) {
        const query = {}
        return axios({
            url: '/cms/villager/Villager/saveVillager',
            method: 'POST',
            params: query,
            data: villager
        });
    }

    //修改保存村民信息
    static updateVillager (villager) {
        const query = {}
        return axios({
            url: '/cms/villager/Villager/updateVillager',
            method: 'POST',
            params: query,
            data: villager
        });
    }
}
