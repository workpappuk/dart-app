
import React from 'react';
import { Modal, Portal, Text, Button, useTheme } from 'react-native-paper';
import { View, StyleSheet } from 'react-native';

export interface AppAlertProps {
    visible: boolean;
    title?: string;
    message: string;
    onDismiss: () => void;
    confirmText?: string;
    onConfirm?: () => void;
    cancelText?: string;
    onCancel?: () => void;
}

export default function AppAlert({
    visible,
    title,
    message,
    onDismiss,
    confirmText = 'OK',
    onConfirm,
    cancelText,
    onCancel,
}: AppAlertProps) {
    const theme = useTheme();
    return (
        <Portal>
            <Modal
                visible={visible}
                onDismiss={onDismiss}
                contentContainerStyle={[
                    styles.container,
                    {
                        backgroundColor: theme.colors.background,
                        borderColor: theme.colors.primary,
                        shadowColor: theme.colors.primary,
                    },
                ]}
                dismissable={true}
            >
                {title && (
                    <Text variant="titleMedium" style={{ color: theme.colors.primary, marginBottom: 8, textAlign: 'center', fontWeight: 'bold' }}>{title}</Text>
                )}
                {message && (
                    <Text style={{ color: theme.colors.onBackground, marginBottom: 20, textAlign: 'center', fontSize: 16 }}>{message}</Text>
                )}
                <View style={styles.buttonRow}>
                    {cancelText && (
                        <Button mode="outlined" onPress={onCancel} style={[styles.button, { borderColor: theme.colors.error }]} textColor={theme.colors.error}>
                            {cancelText}
                        </Button>
                    )}
                    <Button mode="contained" onPress={onConfirm || onDismiss} style={styles.button}>
                        {confirmText}
                    </Button>
                </View>
            </Modal>
        </Portal>
    );
}

const styles = StyleSheet.create({
    container: {
        marginHorizontal: 24,
        borderRadius: 18,
        paddingVertical: 28,
        paddingHorizontal: 24,
        borderWidth: 1.5,
        alignItems: 'center',
        justifyContent: 'center',
        elevation: 8,
        shadowOffset: { width: 0, height: 4 },
        shadowOpacity: 0.25,
        shadowRadius: 12,
        zIndex: 2,
    },
    buttonRow: {
        flexDirection: 'row',
        justifyContent: 'center',
        width: '100%',
        gap: 12,
        marginTop: 8,
    },
    button: {
        marginLeft: 0,
        minWidth: 100,
        borderRadius: 8,
    },
});