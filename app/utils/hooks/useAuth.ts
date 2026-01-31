import { RootState, setToken } from "@/app/redux/store";
import { useDispatch, useSelector } from "react-redux";
import { clearUserSessionToken, setUserSessionToken, forceLogoutUser } from '@/app/utils/axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { SESSION_TOKEN_KEY } from '@/app/utils/constants';
import { useRouter } from "expo-router";

export default function useAuth() {
    const session = useSelector((state: RootState) => state.session);
    const dispatch = useDispatch();
    const router = useRouter();

    const intiateSession = async (token: string) => {
        dispatch(setToken(token)); 
        await AsyncStorage.setItem(SESSION_TOKEN_KEY, token);
        setUserSessionToken(token);
        router.push('/'); 
    };

    const revokeSession = async () => {
        await forceLogoutUser();
        router.push('/'); 
    };

    return {
        intiateSession,
        revokeSession,
    };
}