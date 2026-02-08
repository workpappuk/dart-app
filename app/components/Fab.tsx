import React from 'react'
import { FAB, Portal, useTheme } from 'react-native-paper'

function Fab() {
    const [state, setState] = React.useState({ open: false });
    const { colors } = useTheme();

    const onStateChange = ({ open }: { open: boolean }) => setState({ open });

    const { open } = state;
    return (
        <Portal>
            <FAB.Group
                fabStyle={{
                    backgroundColor: colors.scrim,
                    borderColor: colors.primary,
                    borderWidth: 1,
                }}
                open={open}
                visible
                icon={open ? 'calendar-today' : 'plus'}
                actions={[
                    { icon: 'plus', onPress: () => console.log('Pressed add') },
                    {
                        icon: 'star',
                        label: 'Star',
                        onPress: () => console.log('Pressed star'),
                    },
                    {
                        icon: 'email',
                        label: 'Email',
                        onPress: () => console.log('Pressed email'),
                    },
                    {
                        icon: 'bell',
                        label: 'Remind',
                        onPress: () => console.log('Pressed notifications'),
                    },
                ]}
                onStateChange={onStateChange}
                onPress={() => {
                    if (open) {
                        // do something if the speed dial is open
                    }
                }}
            />
        </Portal>
    )
}

export default Fab