package engine.repositories;

import engine.logic.RepositoryManager;

import java.util.*;

public class RepositoriesManager {
    private final Map<String, UserData> m_UsersDataHashMap;
    private final Map<String, List<RepositoryManager>> m_RepositoriesListHashMap;

    public RepositoriesManager() {
        m_UsersDataHashMap = new HashMap<>();
        m_RepositoriesListHashMap = new HashMap<>();
    }

    private synchronized void addUserData(String i_UserName, UserData i_UserData) {
        if (!isUserExists(i_UserName) && i_UserData.getRepositoriesDataList() != null) {
            m_UsersDataHashMap.put(i_UserName, i_UserData);
        }
    }

    public synchronized void addRepositoryData(String i_Username, RepositoryData i_RepositoryData, RepositoryManager i_RepositoryManager) {


        if (!isUserExists(i_Username)) {
            List<RepositoryManager> repositoriesManagerList=new LinkedList<>();
            repositoriesManagerList.add(i_RepositoryManager);

            List<RepositoryData> repositoriesDataList = new LinkedList<>();
            repositoriesDataList.add(i_RepositoryData);
            UserData userData = new UserData(repositoriesDataList, i_Username);
            addUserData(i_Username, userData);
            m_RepositoriesListHashMap.put(i_Username,repositoriesManagerList);


        } else {
            m_UsersDataHashMap.get(i_Username).AddRepositoryData(i_RepositoryData);
            m_RepositoriesListHashMap.get(i_Username).add(i_RepositoryManager);
        }
    }

    public synchronized Set<String> getUsers() {
        return Collections.unmodifiableSet(m_UsersDataHashMap.keySet());
    }

    public boolean isUserExists(String i_UserName) {
        return m_UsersDataHashMap.keySet().contains(i_UserName);
    }

    public UserData GetUserData(String i_UserName) {
        UserData userData = null;
        if (isUserExists(i_UserName)) {
            userData = m_UsersDataHashMap.get(i_UserName);
        }
        return userData;
    }

    public Map<String, UserData> getUsersDataHashMap() {
        return m_UsersDataHashMap;
    }

    public int GetVersion() {
        return m_UsersDataHashMap.size();
    }


}


