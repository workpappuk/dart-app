
import React, { useEffect, useState } from 'react';
import { Surface } from 'react-native-paper';
import { AppHeader } from '../components/core/AppHeader';



export default function ExploreScreen() {

   useEffect(() => {
   }, []);

   return (
      <>
         <Surface style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <AppHeader title="Explore" />

         </Surface>
      </>
   );
}